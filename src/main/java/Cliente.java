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
                System.out.println("Cliente" + id + " sentou");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Barbearia.cadeira.acquire();
                Barbearia.sofa.release();

                for(Barbeiro b : Barbearia.barbeiros){
                    if (!b.ocupado){
                        b.ocupado=true;
                        b.cliente_atual = id;
                    }
                    System.err.println(b.id);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Cliente" + id + " foi atendido");
            Barbearia.cadeira.release();
            Barbearia.LOTACAO--;

        } else {
            System.out.println("Não haviam lugares.");
        }
    }

}
