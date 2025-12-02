package interface_adapter.class_average;

import interface_adapter.ViewModel;

public class ClassAverageViewModel extends ViewModel<ClassAverageState> {

    public ClassAverageViewModel() {
        super("average");
        this.setState(new ClassAverageState());
    }

}
