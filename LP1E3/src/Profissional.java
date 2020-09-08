public class Profissional {
    private String nome;
    private String cargo;
    private double salario;
    private boolean trabalhando;

    public Profissional(String nome, String cargo, double salario) {
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }

    public void descricao() {
        System.out.printf("%s é um(a) %s, recebe R$%.2f e no momento %s trabalhando%n", nome, cargo, salario, trabalhando == true ? "está" : "não está");
    }

    public void trabalhar() {
        trabalhando = true;
    }

    public void tirarFerias() {
        trabalhando = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isTrabalhando() {
        return trabalhando;
    }

    public void setTrabalhando(boolean trabalhando) {
        this.trabalhando = trabalhando;
    }
}
