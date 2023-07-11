package process;

public class FilterChain {
     public FilterChain(Filter filter, FilterChain filterChain){
         this.filter = filter;
         this.nextFilter = filterChain;

         if(this.nextFilter == null){
             this.nextFilter = new FilterChain(null,this);
         }
     }

     public void next(String name){
         if(this.filter != null){
             this.filter.handle(name,this.nextFilter);
         }
     }

     private Filter filter;
     private FilterChain nextFilter;
}
