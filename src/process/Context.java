package process;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Context {

    private List<Class<? extends Filter>> filters = new ArrayList<>();

    public void addFilter(Class<? extends Filter> filter) {
        this.filters.add(filter);
    }
    public static void main(String[] args) {

        Context context = new Context();

        context.addFilter(Filter_1.class);
        context.addFilter(Filter_2.class);
        context.addFilter(Filter_3.class);
        context.addFilter(Filter_4.class);

        FilterChain chain = context.newFilterChain();

        chain.next("test");
    }

    private FilterChain newFilterChain() {
        Filter filter = null;
        FilterChain filterChain = null;

        for(int i = filters.size() -1; i >=0; i--){
            filter = newFilter(filters.get(i));

            filterChain = new FilterChain(filter,filterChain);
        }
        return filterChain;
    }

    private Filter newFilter(Class<? extends Filter> filter){
        try{
            return filter.getDeclaredConstructor().newInstance();
        } catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
            return null;
        }
    }

    static class Filter_1 implements Filter{

        @Override
        public void handle(String name, FilterChain filterChain){
            if(name.equals("test")){
                System.out.println(" -> " + name);
                System.out.println("Filter_1");
            }
            filterChain.next(name+"1");
        }
    }
    static class Filter_2 implements Filter{

        @Override
        public void handle(String name, FilterChain filterChain){
            if(name.equals("test1")){
                System.out.println(" -> " + name);
                System.out.println("Filter_2");
            }
            filterChain.next(name+"2");
        }
    }
    static class Filter_3 implements Filter{

        @Override
        public void handle(String name, FilterChain filterChain){
            if(name.equals("test12")){
                System.out.println(" -> " + name);
                System.out.println("Filter_3");
            }
            filterChain.next(name);
        }
    }

    static class Filter_4 implements Filter{

        @Override
        public void handle(String name, FilterChain filterChain){
            if(name.equals("test")){
                System.out.println(" -> " + name);
                System.out.println("Filter_4");
            }
            filterChain.next(name);
        }
    }
}
