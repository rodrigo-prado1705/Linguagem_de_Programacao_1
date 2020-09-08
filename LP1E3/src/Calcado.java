public class Calcado {
    private String nome;
    private double preco;
    private boolean calcado;
    private boolean usado;

    public Calcado(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public void descricao() {
        System.out.printf("%s custa R$%.2f e %s usado%n", nome, preco, usado == true ? "está" : "não está");
    }

    public void calcar() {
        calcado = true;
        if (!usado) {
            usado = true;
            System.out.println("Você calçou o sapato, e agora ele está usado");
        } else {
            System.out.println("Você calçou o sapato");
        }
    }

    public void descalcar() {
        calcado = false;
        System.out.println("Você descalçou o sapato");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public boolean isCalcado() {
        return calcado;
    }

    public void setCalcado(boolean calcado) {
        this.calcado = calcado;
    }
}
