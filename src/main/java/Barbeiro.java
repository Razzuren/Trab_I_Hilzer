public class Barbeiro implements Runnable{

    int id;
    int cliente_atual;
    boolean ocupado;

    public Barbeiro(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while(Barbearia.ABERTA){
            if(ocupado){
                System.out.println("Barbeiro" + id + " esta atendendo o Cliente"+cliente_atual);

                try {
                    Thread.sleep(5000);
                    Barbearia.pagamento.acquire();
                    Thread.sleep(2500);
                    Barbearia.pagamento.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
