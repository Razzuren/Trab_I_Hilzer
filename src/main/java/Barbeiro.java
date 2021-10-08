import java.util.Random;

public class Barbeiro implements Runnable{
    //Váriáveis de Instância
    int id;
    Barbearia barbearia;
    Random random = new Random();

    //Variáveis de controle
    volatile int cliente_atual;
    volatile boolean ocupado = false;

    //Construtor
    public Barbeiro(int id, Barbearia barbearia) {
        this.id = id;
        this.barbearia = barbearia;
    }

    @Override
    public void run() {
        //Trabalha enquanto a barbearia estiver aberta
        while(Barbearia.ABERTA){

            //Se tiver cliente esperando na cadeira
            //o barbeiro atende
            if(ocupado){

                //Atualiza o barbeiro que está
                // livre para atender e atende o cliente
                try {
                    barbearia.atualizaBarbeiro(id);
                    System.out.println("Barbeiro" + id + " esta atendendo o Cliente"+cliente_atual);
                    Thread.sleep(random.nextInt(3000));

                    //Solicita a POS para receber pagamento
                    barbearia.pagamento.acquire();
                    Thread.sleep(random.nextInt(1000));
                    System.out.println("Cliente" + cliente_atual + " " +
                            "foi atendido pelo Barbeiro"
                            + id);
                    //Finaliza a thread do Cliente que atendeu
                    try {
                        barbearia.t_clientes.get(cliente_atual).interrupt();
                        barbearia.t_clientes.get(cliente_atual).join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Libera a cadeira para um novo cliente
                    //e a POS para outro barbeiro
                    barbearia.cadeira.release();
                    barbearia.pagamento.release();

                    //Fica livre para um novo atendimento
                    //e atualiza a lotação na barbearia
                    ocupado=false;
                    barbearia.ID_BARBEIRO_LIVRE = id;
                    barbearia.LOTACAO--;


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



