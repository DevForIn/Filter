package process;

public interface Filter {
    void handle(String name,  FilterChain filterChain);
}
