package main_package.controller.handlers;

import main_package.model.user.Session;
import main_package.model.user.State;
import main_package.view.View;

public class Logout implements Handler {
    @Override
    public Session execute(Session session, View view) {
        session.setState(State.UNLOGGED);
        return session;
    }
}
