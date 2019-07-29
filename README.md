# Machine Learning Models

This repo contains two Machine Learning models that we created to perform two tasks:
<ol>
  <li>Plant Recognition task</li>
  <li>Plant Wilt Detection Task</li>
</ol> 

The first model is capable of identifying the species of the plant to help people unfamiliar with agriculture (like villa owners) as well as those who find it more convenient to automate the agriculture process.

The other is a model that was made to detect wilting in different kinds of plants in order for them to receive the necessary care, or for the plant to be removed to minimize wasting water watering dead plants.

Both models use the same architecture with little diferences in the hyperparameters that were tuned for each task seperately.

## Model Architecture
The model was implemented using transfer learning technique in order to get acceptable results, although the model was trained on small amount of images that we collected manually. 

The architecture consisted of densenet model with 121 layers pre-trained on imagnet dataset with 1000 classes, a single convolutional layer and a final dense output layer.

Different parameters were used in each model as follows:
#### Plant Recognition Model

This model was trained to recognize 5 different kinds of plants as an example for plants that most of the already implemented APIs werent able to classify.

The dataset was collected manually to ensure stable distribution of data over the dataset and over 400 images of 5 different kinds of plants and achieved an accuracy of over 90% (95% on training data) in just 60 epochs.
The number of epochs was automatically determined by the callbacks early stopping function where the model should stop 10 epochs after there is no change more than 1*10^-3 in the validation error.
The learning rate used was 1*10^-5 instead of the default learning rate.

<div style="text-align:center"><img src="https://github.com/mohamedazab/smart-irrigation-system/blob/ML-models/assets/dense.jpg" /</div>

##### Plant Wilt Detection

This model was trained to detect wilting in grass.

The dataset used contained around 140 images of either normal grass or wilting/wilted grass. It achieved an accuracy of around 90% on the test set in 17 epochs.

![grass predictions](https://raw.githubusercontent.com/mohamedazab/smart-irrigation-system/ML-models/assets/grass.jpg)

