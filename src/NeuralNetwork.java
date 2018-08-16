import java.util.Random;

class NeuralNetwork {

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
    NeuralNetwork(int ni, int[] nl, int no) {
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
    private NeuralNetwork(int ni, int[] nl, int no, Neuron[][] network) {
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
    double[] predict(double[] inputs) {
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

    void print() {
        for (Neuron[] layer : this.network) {
            for (Neuron n : layer) {
                n.print();
            }
            System.out.println("===");
        }
    }

    /**
     * Mutates the weights of the Neural Network
     * @param mr - mutation rate
     */
    void mutate(double mr) {
        for (Neuron[] layer : this.network) {
            for (Neuron n : layer) {
                n.mutate(mr);
            }
        }

    }

    /**
     * Copys a Neural Network
     * @return - an exact copy of the Neural Network
     */
    NeuralNetwork copy() {

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

        void print() {
            System.out.println(this.bias);
            for (double weight : this.weights) {
                System.out.println(weight);
            }
            System.out.println();
        }

        /**
         * Fires the artificial Neuron
         * @param inputs - double array of inputs to Neuron
         * @return - value between -1 and 1
         */
        double fire(double[] inputs) {
            if (this.weights.length != inputs.length) {
                return 0;
            }
            double val = this.bias;
            for (int i = 0; i < inputs.length; i++) {
                val += this.weights[i] * inputs[i];
            }
            //System.out.println("V"+val);
            return Math.tanh(val/20); // Activation function tanh(x)
        }

        /**
         * Mutates a single rangle weight (including bias) in the Neuron
         * @param rate - how much weight should change
         */
        void mutate(double rate) {
            Random r = new Random();
            for (int i = 0; i < this.weights.length + 1; i++) {
                if (i < this.weights.length) {
                    this.weights[i] += r.nextGaussian() * rate;
                    this.weights[i] = Math.min(Math.max(-1,this.weights[i]), 1);
                } else {
                    this.bias += r.nextGaussian() * rate;
                }
            }
        }

        Neuron copy() {
            return new Neuron(this.weights, this.bias);
        }
    }
}
