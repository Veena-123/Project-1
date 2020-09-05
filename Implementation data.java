Data weatherDataInput = new Data( "data", "inmet_13_14_input.csv"
);
Data weatherDataOutput = new Data( "data", "inmet_13_14_output.csv"
);
//sets the normalisation type
NormalizationTypesENUM NORMALIZATION_TYPE = Data.
NormalizationTypesENUM.MAX_MIN_EQUALIZED;
try {
double[][] matrixInput = weatherDataInput.rawData2Matrix(
weatherDataInput );
double[][] matrixOutput = weatherDataOutput.rawData2Matrix(
weatherDataOutput );
//normalise the data
double[][] matrixInputNorm = weatherDataInput.normalize(
matrixInput, NORMALIZATION_TYPE );
double[][] matrixOutputNorm = weatherDataOutput.normalize(
matrixOutput, NORMALIZATION_TYPE );
Then, the main method builds a neural network with four hidden neurons and sets
the training dataset, as shown in the following code:
NeuralNet n1 = new NeuralNet();
n1 = n1.initNet(4, 1, 4, 1);
n1.setTrainSet( matrixInputNorm );
n1.setRealMatrixOutputSet( matrixOutputNorm );
n1.setMaxEpochs( 1000 );
n1.setTargetError( 0.00001 );
n1.setLearningRate( 0.5 );
n1.setTrainType( TrainingTypesENUM.BACKPROPAGATION );
n1.setActivationFnc( ActivationFncENUM.SIGLOG );
n1.setActivationFncOutputLayer(ActivationFncENUM.LINEAR);
NeuralNet n1Trained = new NeuralNet();
n1Trained = n1.trainNet( n1 );
System.out.println();
Here, the network is trained, and then, the charts of the error are plotted. The
following lines show how the chart class is used:
Chart c1 = new Chart();
c1.plotXYData( n1.getListOfMSE().toArray(), "MSE Error", "Epochs",
"MSE Value" );
//TRAINING:
double[][] matrixOutputRNA = n1Trained.getNetOutputValues( n1Trained
);
double[][] matrixOutputRNADenorm = new Data().denormalize(
matrixOutput, matrixOutputRNA, NORMALIZATION_TYPE);
ArrayList<double[][]>listOfArraysToJoin = new ArrayList<double[]
[]>();
listOfArraysToJoin.add( matrixOutput );
listOfArraysToJoin.add( matrixOutputRNADenorm );
double[][] matrixOutputsJoined = new Data().joinArrays(
listOfArraysToJoin );
Chart c2 = new Chart();
c2.plotXYData( matrixOutputsJoined, "Real x Estimated -
Training Data", "Weather Data", "Temperature (Celsius)", Chart.
ChartPlotTypeENUM.COMPARISON );
