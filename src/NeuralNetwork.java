import java.util.Random;

public class NeuralNetwork {

    private int nInputs;
    private int[] nLayers;
    private int nOutputs;
    private Neuron[][] network;

    /**
     * Creates a new Neural Network
     * @param ni - number of inputs
     * @param nl - array of hidden layer sizes
     * @param no = number of outputs
     */
    public NeuralNetwork(int ni, int[] nl, int no) {
        this.nInputs = ni;
        this.nLayers = nl;
        this.nOutputs = no;
        this.network = new Neuron[this.nLayers.length + 1][];

        int numInp = this.nInputs;
        for (int i = 0; i < this.network.length; i++) {
            int lSize = no;
            if (i < this.nLayers.length) {
                lSize = this.nLayers[i];
            }
            this.network[i] = new Neuron[lSize];
            for (int j = 0; j < this.network[i].length; j++) {
               this.network[i][j] = new Neuron(numInp);
            }
            numInp = this.network[i].length;
        }
    }

    /**
     * Used to copy a Neural network
     * @param ni - number of inputs
     * @param nl - array of hidden layer sizes
     * @param no - number of outputs
     * @param network - network of Neurons
     */
    public NeuralNetwork(int ni, int[] nl, int no, Neuron[][] network) {
        this.nInputs = ni;
        this.nLayers = nl;
        this.nOutputs = no;
        this.network = network;
    }

    /**
     * Predicts output values given input values
     * @param inputs - double array of input values
     * @return - network output predictions
     */
    public double[] predict(double[] inputs) {
        double[] vals = inputs;
        for (int i = 0; i < this.network.length; i++) {
            vals = feedForward(vals, i);
        }
        return vals;
    }

    /**
     * Feeds inputs through 1 layer of network.
     * @param inputs - double array of inputs
     * @param layer - layer index
     * @return - layer outputs
     */
    private double[] feedForward(double[] inputs, int layer) {
        double[] out = new double[this.network[layer].length];
        for (int i = 0; i < out.length; i++) {
            out[i] = this.network[layer][i].fire(inputs);
        }
        return out;
    }

    public void print() {
        for (int i = 0; i < this.network.length; i++) {
            for (int j = 0; j < this.network[i].length; j++) {
                this.network[i][j].print();
            }
            System.out.println("===");
        }
    }

    /**
     * Mutates the weights of the Neural Network
     * @param mr - mutation rate
     */
    public void mutate(double mr) {
        Random r = new Random();
        for (int i = 0; i < this.network.length; i++) {
            for (int j = 0; j < this.network[i].length; j++) {
                this.network[i][j].mutate(mr);
            }
        }

    }

    /**
     * Copys a Neural Network
     * @return - an exact copy of the Neural Network
     */
    public NeuralNetwork copy() {

        Neuron[][] newNetwork = new Neuron[this.network.length][];
        for (int i = 0; i < this.network.length; i++) {
            newNetwork[i] = new Neuron[this.network[i].length];
            for (int j = 0; j < this.network[i].length; j++) {
                newNetwork[i][j] = this.network[i][j].copy();
            }
        }

        return new NeuralNetwork(this.nInputs, this.nLayers, this.nOutputs, newNetwork);
    }


    class Neuron {
        double[] weights;
        double bias;

        /**
         * Creates a new Neuron
         * @param nWeights - number of weights in Neuron (excluding bias)
         */
        Neuron(int nWeights) {
            Random r = new Random();
            this.weights = new double[nWeights];
            this.bias = 2 * r.nextDouble() - 1;
            for (int i = 0; i < this.weights.length; i++) {
                this.weights[i] = 2 * r.nextDouble() - 1;
            }
        }

        /**
         * Creates a Neuron with predefined weights and bias
         * @param weights - double array of Neuron weights
         * @param bias - Neuron bias
         */
        Neuron(double[] weights, double bias) {
            this.weights = weights.clone();
            this.bias = bias;
        }

        public void print() {
            System.out.println(this.bias);
            for (int i = 0; i < this.weights.length; i++) {
                System.out.println(this.weights[i]);
            }
            System.out.println();
        }

        /**
         * Fires the artificial Neuron
         * @param inputs - double array of inputs to Neuron
         * @return - value between -1 and 1
         */
        public double fire(double[] inputs) {
            if (this.weights.length != inputs.length) {
                return 0;
            }
            double val = this.bias;
            for (int i = 0; i < inputs.length; i++) {
                val += this.weights[i] * inputs[i];
            }
            //System.out.println("V"+val);
            return Math.tanh(val); // Activation function tanh(x)
        }

        /**
         * Mutates a single rangle weight (including bias) in the Neuron
         * @param rate - how much weight should change
         */
        public void mutate(double rate) {
            Random r = new Random();
            for (int i = 0; i < this.weights.length + 1; i++) {
                if (i < this.weights.length) {
                    this.weights[i] += r.nextGaussian() * rate;
                } else {
                    this.bias += r.nextGaussian();
                }
            }
        }

        public Neuron copy() {
            return new Neuron(this.weights, this.bias);
        }
    }
}
