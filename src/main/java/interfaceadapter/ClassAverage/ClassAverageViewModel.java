package interfaceadapter.ClassAverage;

import interfaceadapter.ViewModel;

public class ClassAverageViewModel extends ViewModel<ClassAverageState> {

    public ClassAverageViewModel() {
        super("average");
        this.setState(new ClassAverageState());
    }

}
