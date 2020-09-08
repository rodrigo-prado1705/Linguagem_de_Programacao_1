public class Lugar {
    private String nome;
    private String tipo;
    private int nota;
    private int qtdViagens;

    public Lugar(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
        this.nota = 0;
    }

    public void viajar() {
        System.out.printf("Agora você viajou para %s %d vez(es)%n", nome, ++qtdViagens);
    }

    public void descricao() {
        System.out.printf("%s é um(a) %s. Você avaliou este lugar com a nota %d, e você viajou para este lugar %d vez(es)%n", 
                        nome, tipo, nota, qtdViagens);
    }

    public void darNota(int nota) {
        if (qtdViagens > 0) {
            this.nota = nota;
        } else {
            System.out.println("Você não pode dar nota a este lugar, pois ainda não o visitou");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
