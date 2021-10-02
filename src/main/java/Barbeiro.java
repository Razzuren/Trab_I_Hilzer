public class Barbeiro implements Runnable{

    int id;
    int cliente_atual;
    boolean ocupado = false;

    public Barbeiro(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while(Barbearia.ABERTA){
            if(ocupado){
                try {
                    System.out.println("Barbeiro" + id + " esta atendendo o Cliente"+cliente_atual);
                    Thread.sleep(2000);
                    Barbearia.pagamento.acquire();
                    Thread.sleep(1000);
                    System.out.println("Cliente" + cliente_atual + " " +
                            "foi atendido pelo Barbeiro"
                            + id);

                    Barbearia.cadeira.release();
                    Barbearia.pagamento.release();

                    try {
                        Barbearia.limpar.acquire();
                        Barbearia.limpaThreads(cliente_atual);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ocupado=false;
                    Barbearia.ID_BARBEIRO_LIVRE = id;

                    Barbearia.LOTACAO--;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
