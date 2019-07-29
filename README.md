# Machine Learning Models

We created two machine learning models using tensorflow in our project.

One is used to identify different kinds of plants in order to aid those who are unfamiliar with agriculture as well as those who find it more convenient to automate the process.

The other is a model that was made to detect wilting in different kinds of plants in order for them to receive the necessary care, or for the plant to be removed.

Both models have one covolution layer, one pooling layer and one fully connected layer. Better results can be achieved with a deeper model, however the result shown here suffice at least for a prototype.

## Plant Recognition Model
This model was trained to recognize 5 different kinds of plants.

The dataset used contained over 400 images of 5 different kinds of plants and achieved an accuracy of over 85% (95% on training data) in just 60 epochs.

## Plant Wilt Detection
This model was trained to detect wilting in grass.

The dataset used contained around 140 images of either normal grass or wilting/wilted grass. It achieved an accuracy of around 70% in 7 epochs and showed no significant improvement. However making a deeper network can probably train it better.