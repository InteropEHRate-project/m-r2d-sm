package eu.interopehrate.mr2dsm;

import eu.interopehrate.mr2dsm.base.BasePresenter;
import eu.interopehrate.mr2dsm.base.BaseView;

public class MainContract {
    public interface View extends BaseView<Presenter> {

    }

    public interface Presenter extends BasePresenter {
        void requestToken(String username, String password);
        void authenticate(String token);
    }
}
