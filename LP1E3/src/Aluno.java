import java.util.Arrays;

public class Aluno {
    private String nome;
    private String ra;
    private double[] notas = new double[4];

    public Aluno(String nome, String ra) {
        this.nome = nome;
        this.ra = ra;
    }

    public void descricao() {
        System.out.printf("Nome: %s, RA: %s, Notas: %s%n", nome, ra, String.join(", ", Arrays.toString(notas).replaceAll("\\[|\\]", "")));
    }

    public void media() {
        double sum = 0;

        for (double n : notas) {
            sum += n;
        }
        System.out.printf("Média: %.1f%n", sum / notas.length);
    }

    public void darNota(double nota, int n) {
        notas[n] = nota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public double[] getNotas() {
        return notas;
    }

    public void setNotas(double[] notas) {
        this.notas = notas;
    }
}
