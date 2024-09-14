package mockhelper;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

public class CallChecker<T extends Enum<T>> {
    private final Queue<CalledMethodTuple<T>> queue;
    private final Map<T, Queue<Object>> returnValues;

    public CallChecker(){
        queue = new LinkedList<>();
        returnValues = new HashMap<>();
    }

    public void checkQueueIsEmpty(){
        assertThat(queue.isEmpty()).isTrue();
    }


    public Object addCall(T method, Object... args){
        var element = new CalledMethodTuple<>(method, args);
        queue.offer(element);
        return getNextReturnValue(method);
    }

    public Object[] checkNextMethod(T method, Object... args){
        var calledMethodTuple = queue.poll();
        assertThat(method).isEqualTo(calledMethodTuple.getMethod());

        RecursiveComparisonConfiguration configuration = new RecursiveComparisonConfiguration();
        configuration.setIgnoreAllActualNullFields(true);
        assertThat(args)
                .usingRecursiveFieldByFieldElementComparator(configuration)
                .containsExactly(calledMethodTuple.getArgs());
        return calledMethodTuple.getArgs();
    }

    //when you like give a value in the test
    public void addMethodCallingValue(T method, Object returnValue) {
        returnValues.computeIfAbsent(method, k -> new LinkedList<>()).offer(returnValue);
    }


    private Object getNextReturnValue(T method) {
        Queue<Object> values = returnValues.get(method);
        if (values != null && !values.isEmpty()) {
            return values.poll();
        }
        return null;
    }

    private static class CalledMethodTuple<T extends Enum<T>> {
        private final T method;
        private final Object[] args;

        public CalledMethodTuple(T method, Object... args) {
            this.method = method;
            this.args = args;
        }

        public T getMethod() {
            return method;
        }

        public Object[] getArgs() {
            return args;
        }
    }
}
