import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Barbearia{

    static volatile boolean ABERTA = true;
    static final int  N_BARBEIROS = 3;
    static final int N_CADEIRAS = 3;
    static final int LUGARES_SOFA = 4;
    static final int CAIXA = 1;
    static  int LOTACAO = 0;

    static Semaphore sofa;
    static Semaphore cadeira;
    static Semaphore pagamento;

    static ArrayList<Barbeiro> barbeiros;


    public Barbearia () throws InterruptedException {
        cadeira = new Semaphore(N_CADEIRAS,true);
        sofa = new Semaphore (LUGARES_SOFA,true);
        pagamento = new Semaphore(CAIXA,true);

        ArrayList<Thread> clientes = new ArrayList<Thread>();
        ArrayList<Thread> t_barbeiros = new ArrayList<Thread>();

        for(int i=0; i < N_BARBEIROS;i++){
            Barbeiro b = new Barbeiro(barbeiros.size());
            Thread t = new Thread(b);
            barbeiros.add(b);
            t_barbeiros.add(t);
            t.start();
        }

        while (ABERTA){
            Thread.sleep(1000);
            Cliente c = new Cliente(clientes.size());
            Thread t = new Thread(c);
            clientes.add(t);
            t.start();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new Barbearia();
    }

}
