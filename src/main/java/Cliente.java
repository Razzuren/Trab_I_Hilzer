import java.util.concurrent.BrokenBarrierException;

public class Cliente implements Runnable{

    int id;

    public Cliente (int id) {
        this.id = id;
    }

    @Override
    public void run()  {
        if(Barbearia.LOTACAO < 20){
            System.out.println("Cliente" + id + " Chegou");
            try {
                Barbearia.LOTACAO++;
                Barbearia.sofa.acquire();
                System.out.println("Cliente" + id + " sentou no sofá");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Barbearia.cadeira.acquire();
                Barbearia.sofa.release();
                System.out.println("Cliente" + id + " sentou na cadeira");

                Barbearia.barbeiros.get(Barbearia.ID_BARBEIRO_LIVRE).ocupado=true;
                Barbearia.barbeiros.get(Barbearia.ID_BARBEIRO_LIVRE).cliente_atual=id;
                if(id<2) Barbearia.ID_BARBEIRO_LIVRE++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
