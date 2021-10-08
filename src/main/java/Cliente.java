public class Cliente implements Runnable{

    //Váriáveis de Instância
    int id;
    Barbearia barbearia;

    //Construtor
    public Cliente (int id, Barbearia barbearia) {

        this.id = id;
        this.barbearia = barbearia;
    }

    @Override
    public void run()  {

        System.out.println("Cliente" + id + " Chegou");

        //Atualiza a lotação da barbearia
        // e solicita um lugar no sofa
        try {
            barbearia.LOTACAO++;
            barbearia.sofa.acquire();
            System.out.println("Cliente" + id + " sentou no sofá");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Solicita uma cadeira de atendimento
        //e libera um lugar no sofá
        try {
            barbearia.cadeira.acquire();
            barbearia.barbeiros.get(barbearia.ID_BARBEIRO_LIVRE).ocupado = true;
            barbearia.barbeiros.get(barbearia.ID_BARBEIRO_LIVRE).cliente_atual = id;
            barbearia.sofa.release();
            System.out.println("Cliente" + id + " sentou na cadeira");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
