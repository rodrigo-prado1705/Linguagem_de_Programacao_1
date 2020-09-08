public class Instrumento {
    private String nome;
    private int numeroCordas;
    private boolean tocando;
    private boolean afinado;

    public Instrumento(String nome, int numeroCordas) {
        this.nome = nome;
        this.numeroCordas = numeroCordas;
    }

    public void descricao() {
        System.out.printf("%s é um instrumento de %d corda(s). No momento %s tocando e %s afinado%n", 
                            nome, numeroCordas, tocando == true ? "está" : "não está", afinado == true ? "está" : "não está");
    }

    public void tocarOuParar() {
        tocando = !tocando;
    }

    public void afinar() {
        afinado = true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroCordas() {
        return numeroCordas;
    }

    public void setNumeroCordas(int numeroCordas) {
        this.numeroCordas = numeroCordas;
    }

    public boolean isTocando() {
        return tocando;
    }

    public void setTocando(boolean tocando) {
        this.tocando = tocando;
    }

    public boolean isAfinado() {
        return afinado;
    }

    public void setAfinado(boolean afinado) {
        this.afinado = afinado;
    }
}
