package school.sptech.observer;

public interface EmailSubject {
    void adicionarObserver(Observer observer);
    void removerObserver(Observer observer);
    void notificarObservers(String message);
}
