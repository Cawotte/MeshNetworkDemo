package model;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MeshNetworkDisruptor {

    private MeshNetwork meshNetwork;

    private float frequencyRandomDisabler = 1f;
    private float frequencyRandomPathDisabler = 2f;


    public Runnable notifyObserver;

    public MeshNetworkDisruptor(MeshNetwork meshNetwork) {
        this.meshNetwork = meshNetwork;
    }

    public void startRecurrentDisabling() {


        Timer timer = new Timer();

        //setup timer path
        TimerTask recurringPathDisabling = new TimerDisabledRandomNodeOnPath(frequencyRandomPathDisabler);
            //repeat
        timer.schedule(recurringPathDisabling,
                (int)frequencyRandomPathDisabler * 1000,
                (int)frequencyRandomPathDisabler * 1000);

        //setup timer random
        TimerTask recurringRandomDisabling = new TimerDisableRandomNode(frequencyRandomDisabler);
        //repeat
        timer.schedule(recurringRandomDisabling,
                (int)frequencyRandomDisabler * 500,
                (int)frequencyRandomDisabler * 1000);

    }

    private Node getNonRelayRandomNode() {
        Node randomNode;
        do {
            randomNode = meshNetwork.network.getRandomNode();
        } while (randomNode.equals(meshNetwork.relay));

        return randomNode;
    }

    private Node getRandomNodeOnPath() {

        if (meshNetwork.path != null && meshNetwork.path.size() > 1) {

            Random rand = new Random();
            return meshNetwork.path.get(rand.nextInt(meshNetwork.path.size() - 1));
        }

        return null;
    }


    class TimerRenableNode extends TimerTask {

        public Node nodeToEnable;

        public TimerRenableNode(Node nodeToEnable){

            this.nodeToEnable = nodeToEnable;

        }

        public void run() {
            try {

                nodeToEnable.isEnabled = true;
                notifyObserver.run();
;
            } catch (Exception ex) {
                System.out.println("error running thread " + ex.getMessage());
            }
        }
    }

    class TimerDisableRandomNode extends TimerDisableNode {

        public TimerDisableRandomNode(float disabledTime){

            super(disabledTime);

        }

        @Override
        protected Node getNodeToDisable() {
            return getNonRelayRandomNode();
        }
    }

    class TimerDisabledRandomNodeOnPath extends TimerDisableNode {

        public TimerDisabledRandomNodeOnPath(float disabledTime){

            super(disabledTime);

        }

        @Override
        protected Node getNodeToDisable() {
            return getRandomNodeOnPath();
        }
    }

    abstract class TimerDisableNode extends TimerTask {

        public float disabledTime = 1f;

        public TimerDisableNode(float disabledTime){

            this.disabledTime = disabledTime;

        }

        protected abstract Node getNodeToDisable();

        public void run() {
            try {

                //Get a random node to disable
                Node randomNode = getNodeToDisable();

                if (randomNode == null) return;

                randomNode.isEnabled = false;

                //Launch task to re-enable it
                TimerTask timerEnabler = new TimerRenableNode(randomNode);
                Timer timer = new Timer();
                timer.schedule(timerEnabler, (int)disabledTime * 1000); //activate once

                notifyObserver.run();

            } catch (Exception ex) {
                System.out.println("error running thread " + ex.getMessage());
            }
        }
    }


}
