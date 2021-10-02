import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Barbearia{

    static volatile boolean ABERTA = true;
    static final int  N_BARBEIROS = 3;
    static final int N_CADEIRAS = 3;
    static final int LUGARES_SOFA = 4;
    static final int CAIXA = 1;
    static  int LOTACAO = 0;
    static volatile int ID_BARBEIRO_LIVRE;

    static Semaphore sofa;
    static Semaphore cadeira;
    static Semaphore pagamento;
    static Semaphore limpar;

    static ArrayList<Barbeiro> barbeiros;
    static ArrayList<Cliente> clientes;
    static ArrayList<Thread> t_clientes;
    static ArrayList<Thread> t_barbeiros;
    static ArrayList<Integer> threadsFeitas;

    static void limpaThreads(int i) throws InterruptedException {
        t_clientes.get(i).interrupt();
        t_clientes.get(i).join();
        limpar.release();
    }

    public Barbearia () throws InterruptedException {
        cadeira = new Semaphore(N_CADEIRAS,true);
        sofa = new Semaphore (LUGARES_SOFA,true);
        pagamento = new Semaphore(CAIXA,true);
        limpar = new Semaphore(1);

        t_clientes = new ArrayList<>();
        clientes = new ArrayList<>();
        t_barbeiros = new ArrayList<>();
        barbeiros = new ArrayList<>();
        threadsFeitas = new ArrayList<>();
        ID_BARBEIRO_LIVRE = 0;

        for(int i=0; i < N_BARBEIROS;i++){
            Barbeiro b = new Barbeiro(barbeiros.size());
            Thread t = new Thread(b);
            barbeiros.add(b);
            t_barbeiros.add(t);
            t.start();
        }

        while (ABERTA){
            Thread.sleep(3000);
            if(LOTACAO<20) {
                Cliente c = new Cliente(clientes.size());
                Thread t = new Thread(c);
                t_clientes.add(t);
                clientes.add(c);
                t.start();
            }else
                System.err.println(cadeira.availablePermits());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Barbearia();
    }

}
