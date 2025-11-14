package interface_adapter.class_average;

import interface_adapter.ViewModel;

public class ClassAverageViewModel extends ViewModel<ClassAverageState> {

    public ClassAverageViewModel() {
        super("class average");
        this.setState(new ClassAverageState());
    }

    @Override
    public ClassAverageState getState() {
        return super.getState();
    }

    @Override
    public void setState(ClassAverageState state) {
        super.setState(state);
    }
}
