import random
from tensorflow import keras
import json
import numpy
from sklearn.feature_extraction.text import CountVectorizer
import nltk
nltk.download('punkt')
from nltk.stem.lancaster import LancasterStemmer
stemmer = LancasterStemmer()
all_responses = []

dataset = {"data": [
    {"tag": "greeting",
     "patterns": ["Hi","Is anyone there?", "Hello", "Good day", "Whats up","hey","pronto"],
     "responses": ["Hello!", "Hey!", "Hi!"]
     },
    {"tag": "goodbye",
     "patterns": ["Ciao", "See you later", "Goodbye", "I am Leaving", "Have a Good day","bye","bye bye","arrivederci", "goodbye"],
     "responses": ["Sad to see you go :(", "Talk to you later", "Goodbye!"]
     },
    {"tag": "thanks",
     "patterns": ["Thanks", "Thank you", "thanks for your help"],
     "responses": ["You're welcome", "You are welcome", "No problem", "Happy to help you save money"]
     },
    {"tag": "name",
     "patterns": ["whats your name", "what is your name","your name?","what was your name again"],
     "responses": ["My name is Fin", "I'm Fin","I am Fin", "Fin"]
     },
    {"tag": "wellness",
     "patterns": ["how are you?", "are you ok?"],
     "responses": ["Great, how about you?", "Very well thanks, you?","Good, you?", "I'm great thanks, you?"]
     },
    {"tag": "question",
     "patterns": ["can I ask you some questions?", "can i ask u a question","can i ask you something?","can I ask you a series of questions?"],
     "responses": ["Of course, go ahead.", "Of course!","Why not, go ahead!", "Ofc"]
     },
    {"tag": "agree",
     "patterns": ["Agree", "I fully agree with you", "I agree with you","I agree","you are right"],
     "responses": [":)",":-)",";)"]
     },
    {"tag": "disagree",
     "patterns": ["I dont agree", "I do not agree with you", "Dont agree","I dont agree with you","I don't agree with you","I dont agree with you fully"],
     "responses": ["Why? :(","Why though?","Can I know why?","Could you elaborate please?"]
     },
    {"tag": "wellness-response",
     "patterns": ["Im very good thanks", "Im doing good", "Im doing great thank you", "I am good thanks", "I am great thanks"],
     "responses": ["Great", "Good to hear it", "Good", "Glad to hear it"]
     },
    {"tag": "wellness-response-bad",
     "patterns": ["Im not good", "Hanging on", "Im not doing great", "I am not good", "I am not doing great"],
     "responses": ["Why, whats wrong?", "What's wrong?","Can I help you somehow?"]
     },
    {"tag": "income-problems",
     "patterns": ["how to be financial responsible when suddenly there is no income?", "sudden problems with income what can i do?", "no income recently what can i do?"],
     "responses": ["Try to adjust your current budget, and cut the expenses where possible for the near future. \nAt the same time, try to do one of the next steps: \n1. Using money you set aside for low income time periods \n2. Using money you set aside for other purposes. \n3. Postponing payments of loans or mortgages. \n4. Take a loan"]
     },
    {"tag": "rent-problems",
     "patterns": ["i can't pay rent for the next couple of months what can i do?", "no money for rent in the near future what to do?", "I don't have enough money for rent what can i do?"],
     "responses": ["If you know for how long you'll have this problem, ask your land lord to postpone the payments.", "Ask the land lord for a reduced rent temporarily and come to an agreement with him/her how you'll pay back the rest of the money.", "Ask your land lord for a reduced rent.", "Search for a cheaper apartment or a cheaper living arrangement."]
     },
    {"tag": "saving-covid",
     "patterns": ["how can i save money during covid time?", "tell me a way to save money during covid time", "saving money in covid time"],
     "responses": ["Try to avoid reckless decisions, that will influence the long term future such as making changes in insurances.", "Reduce the expenses in areas where the expenses were supposed to be reduced by the changes such as restaurants, shopping etc.", "Temporarily stop transfering money to saving's accounts."]
     },
    {"tag": "payments",
     "patterns": ["is it better to pay with one payment or with multiple payments?", "one payment or multiple payments which option is better?"],
     "responses": ["Regular expenses such as food, electric bill, water bill etc, it's better to pay with one payment. For products you plan to use for a long time, and you purchase one time such as TV, phones, refrigerators etc, you can pay with multiple payments if you know that you can keep up with them." ]
     },
    {"tag": "early-pension",
     "patterns": ["is it a good idea to use pension money early?", "using pension money early good or bad?"],
     "responses": ["It's highly unrecommended! This money is the foundation for your economical stability when you'll be older.","In the future this money will be more valueable for you, so it's highly recommended to wait."]
     },
    {"tag": "electrics",
     "patterns": ["how can i save on my electric bill?", "tell me a way to reduce my electic bill"],
     "responses": ["Use the dryer for laundry only when needed.", "Make sure the laundry machine is full before using it.", "Make sure the dish washer is full before using it.", "Before turning on the AC, try to use the fan.", "On sunny days, avoid using the water heater.", "Turn off the lights when you leave a room (and there is no one else in there of course :P)", "Buy LED light bulbs."]
     },
    {"tag": "shopping",
     "patterns": ["how to avoid spending a lot of money while shopping?", "how to save money while shopping?"],
     "responses": ["Make a list of the things you need and buy only them.", "Avoid impulsive decisions as much as you can while you're in the store.", "Come prepared - compare the prices of the products you want to buy and choose the store that fits your requirements.", "Try to avoid spontaneous shoppings."]
     },
    {"tag": "clothes",
     "patterns": ["how can I save money on clothes?", "tips for saving money on clothes", "give me ways to save money on clothes"],
     "responses": ["Wait for sales and discounts.", "Many brands have outlet stores. You can find there clothes of your favorite brand for less money.", "Check with your friends and family if they have unused clothes you like. I'm sure they'll be happy to share them with you! Always remember that sharing is caring ;)"]
     },
    {"tag": "online",
     "patterns": ["is it better to buy online?", "buying online or in physical store which one is better?"],
     "responses": ["It's better to come to the physical store. \nWhen we buy things online, we tend to misunderstand the amount of money we actually spend."]
     },
    {"tag": "joke",
     "patterns": ["tell me a joke", "can you tell me a joke?", "are you funny?"],
     "responses": ["What did the financially responsible student do to get good grades? \nThey paid off their principal", "Why do fixed interest rates smell so bad? \nBecause they never change", "What kind of debt did the secret agent issue? \nA bond, James Bond."]
     },
    {"tag": "respond-again",
     "patterns": ["can you give me another tip?", "what if i can't do it?", "try another option"],
     "responses": ["Of course! Let me think about it. \nMaybe you can try the following: ", "Sure! Give me a second to think about a new option. \nMaybe you can try the following: "]}
]
}


def tokenize_stem(string_input):

    words = nltk.word_tokenize(string_input)
    stemmed_words = [stemmer.stem(w.lower()) for w in words if w != "?"]

    return stemmed_words


def load_data(data):
    try:
        with open('app/src/main/python/data/english_labels_list.json', 'r') as labels_file, open(
                'app/src/main/python/data/english_pattern_vocab.json', 'r') as pattern_file:
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

    with open('data/english_pattern_vocab.json', 'w') as file:
        json.dump(pattern_vocab, file)

    # Separate labels_vocab keys, sort them and save it to a json file
    for key in labels_vocab:
        labels_dict.append(key)
    labels_dict = sorted(labels_dict)

    with open('data/english_labels_list.json', 'w') as file:
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
        return "Bot: Say something!"
    else:
        if message.lower() in dataset["data"][1]["patterns"]:
            return "Bot: " + random.choice(dataset["data"][1]["responses"])

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
            return "Bot: " + final_response
            all_responses.append([tag, final_response])
            if tag == "wellness-response":
                return "Bot: How can I help you today?"
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
                        return "Bot: How can I help you today?"
                    break

def main(message):

    try:
        labels_dict, pattern_vocab = load_data(dataset)
        loaded_model = keras.models.load_model("app/src/main/python/english_model.h5")
        return chat(loaded_model, labels_dict, pattern_vocab, message)
    except:
        labels_dict, pattern_vocab, input_x, input_y = generate_data(dataset)
        model = create_model(input_x, input_y)
        model.fit(input_x, input_y, epochs=500, batch_size=8)
        model.save("app/src/main/python/english_model.h5")
        return chat(model, labels_dict, pattern_vocab, message)
