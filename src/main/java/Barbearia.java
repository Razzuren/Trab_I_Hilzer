import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barbearia{

    //Variáveis de Controle e Dimensão
    static volatile boolean ABERTA = true;
    static final int  N_BARBEIROS = 3;
    static final int N_CADEIRAS = 3;
    static final int LUGARES_SOFA = 4;
    static final int CAIXA = 1;


    //Gerador Randomico
    Random random = new Random();

    //Variáveis de Controle
    static volatile int LOTACAO = 0;
    static volatile int ID_BARBEIRO_LIVRE;
    Semaphore sofa;
    Semaphore cadeira;
    Semaphore pagamento;

    //Arrays de Threads
    ArrayList<Barbeiro> barbeiros;
    ArrayList<Cliente> clientes;
    ArrayList<Thread> t_clientes;
    ArrayList<Thread> t_barbeiros;
    ArrayList<Integer> threadsFeitas;

    //Construtor
    public Barbearia () throws InterruptedException {
        cadeira = new Semaphore(N_CADEIRAS,true);
        sofa = new Semaphore (LUGARES_SOFA,true);
        pagamento = new Semaphore(CAIXA,true);

        t_clientes = new ArrayList<>();
        clientes = new ArrayList<>();
        t_barbeiros = new ArrayList<>();
        barbeiros = new ArrayList<>();
        threadsFeitas = new ArrayList<>();
        ID_BARBEIRO_LIVRE = 0;

        //Inicializa as threads dos Barbeiros
        for(int i=0; i < N_BARBEIROS;i++){
            Barbeiro b = new Barbeiro(barbeiros.size(),this);
            Thread t = new Thread(b);
            barbeiros.add(b);
            t_barbeiros.add(t);
            t.start();
        }

        //Gera os Clientes Intermitentemente
        while (ABERTA){
            Thread.sleep(random.nextInt(1000));
            if(LOTACAO<20) {
                Cliente c = new Cliente(clientes.size(),this);
                Thread t = new Thread(c);
                t_clientes.add(t);
                clientes.add(c);
                t.start();
            } else{
                System.err.println("Barbearia está lotada!");
            }
        }
    }

    //Atualiza o Barbeiro livre para atender
    public synchronized void atualizaBarbeiro(int id){
        for (Barbeiro b : barbeiros){
            if (b.ocupado == false){
                ID_BARBEIRO_LIVRE = b.id;
                break;
            }
        }
    }

    //Método Principal
    public static void main(String[] args) throws InterruptedException {
        new Barbearia();
    }

}
