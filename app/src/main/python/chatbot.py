import random
from tensorflow import keras
import json
import numpy
from sklearn.feature_extraction.text import CountVectorizer
import nltk
nltk.download('punkt')
from nltk.stem.lancaster import LancasterStemmer
from os.path import dirname, join
stemmer = LancasterStemmer()
all_responses = []

def tokenize_stem(string_input):

    words = nltk.word_tokenize(string_input)
    stemmed_words = [stemmer.stem(w.lower()) for w in words if w != "?"]

    return stemmed_words


def load_data(data):
    try:
        filename = join(dirname(__file__), "english_labels_list.json")
        filename1 = join(dirname(__file__), "english_pattern_vocab.json")
        with open(filename, 'r') as labels_file, open(
                filename1, 'r') as pattern_file:
            labels_dict = json.load(labels_file)
            pattern_vocab = json.load(pattern_file)
            return labels_dict, pattern_vocab
    except:
        labels_dict, pattern_vocab, _, _ = generate_data(
            data)
        return labels_dict, pattern_vocab


def generate_data(data):
    # Separate patterns
    questions = []
    labels = []
    labels_dict = []
    for _, intents in enumerate(data['data']):
        for pattern in intents['patterns']:
            questions.append(pattern)
            labels.append(intents['tag'])

    # Vectorize patterns (Bag of words)
    pattern_vec = CountVectorizer(binary=True, tokenizer=tokenize_stem)
    pattern_matrix = pattern_vec.fit_transform(questions)
    pattern_vocab = pattern_vec.vocabulary_
    input_x = pattern_matrix.todense()
    input_x = numpy.array(input_x)

    # Vectorize labels (Bag of words)
    labels_vec = CountVectorizer(
        binary=True, lowercase=False, token_pattern='[a-zA-Z0-9$&+,:;=?@#|<>.^*()%!-]+')
    labels_matrix = labels_vec.fit_transform(labels)
    labels_vocab = labels_vec.vocabulary_
    input_y = labels_matrix.todense()
    input_y = numpy.array(input_y)

    # Convert pattern_vocab values to integers and save it as a json file
    for key in pattern_vocab:
        pattern_vocab[key] = int(pattern_vocab[key])

    filename = join(dirname(__file__), "english_labels_list.json")
    filename1 = join(dirname(__file__), "english_pattern_vocab.json")

    with open(filename1, 'w') as file:
        json.dump(pattern_vocab, file)

    # Separate labels_vocab keys, sort them and save it to a json file
    for key in labels_vocab:
        labels_dict.append(key)
    labels_dict = sorted(labels_dict)

    with open(filename, 'w') as file:
        json.dump(labels_dict, file)

    return labels_dict, pattern_vocab, input_x, input_y


def create_model(training, output):

    model = keras.Sequential()
    model.add(keras.layers.Dense(8, input_shape=(len(training[0]),)))
    model.add(keras.layers.Dense(8))
    model.add(keras.layers.Dense(len(output[0]), activation='softmax'))

    model.compile(optimizer='adam',
                  loss='categorical_crossentropy',
                  metrics=['accuracy'])

    model.summary()
    return model


def chat(model, labels_dict, pattern_vocab, message):
    if message == '':
        return "Say something!"
    else:

        main_filename = join(dirname(__file__), "english_dataset.json")
        with open(main_filename, 'r') as data:
            dataset = json.load(data)

        if message.lower() in dataset["data"][1]["patterns"]:
            return random.choice(dataset["data"][1]["responses"])

        word_vec = CountVectorizer(
            binary=True, vocabulary=pattern_vocab, tokenizer=tokenize_stem)
        input_dict = [message]
        matrix = word_vec.fit_transform(input_dict)

        results = model.predict([matrix.todense()])[0]
        # print(results)
        results_index = numpy.argmax(results)
        tag = labels_dict[results_index]

        if results[results_index] > 0.6:
            for tg in dataset["data"]:
                if tg['tag'] == tag:
                    responses = tg['responses']
            final_response = random.choice(responses)
            return final_response
            all_responses.append([tag, final_response])
            if tag == "wellness-response":
                return "How can I help you today?"
        else:
            return "Sorry, I dont understand"

        if all_responses[-1][0] == "respond-again":
            count = -2
            while all_responses[count][0] == "respond-again":
                count -= 1
            already_given = all_responses[count][1]
            already_tag = all_responses[count][0]
            while True:
                for tg in dataset["data"]:
                    if tg['tag'] == already_tag:
                        responses = tg['responses']
                new_response = random.choice(responses)
                if new_response != already_given:
                    return "Bot: " + new_response
                    if tag == "wellness-response":
                        return "How can I help you today?"
                    break

def main(message):

    main_filename = join(dirname(__file__), "english_dataset.json")
    with open(main_filename, 'r') as data:
        dataset = json.load(data)
        current_model = join(dirname(__file__), "english_model.h5")
        try:
            labels_dict, pattern_vocab = load_data(dataset)
            loaded_model = keras.models.load_model(current_model)
            return chat(loaded_model, labels_dict, pattern_vocab, message)
        except:
            labels_dict, pattern_vocab, input_x, input_y = generate_data(dataset)
            model = create_model(input_x, input_y)
            model.fit(input_x, input_y, epochs=500, batch_size=8)
            model.save(current_model)
            return chat(model, labels_dict, pattern_vocab, message)