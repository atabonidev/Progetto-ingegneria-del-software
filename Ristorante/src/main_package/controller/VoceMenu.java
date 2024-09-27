package main_package.controller;

import main_package.controller.handlers.Handler;

public class VoceMenu {
    final private String nomeVoce;
    final private Handler handler;
    public VoceMenu(String nomeVoce, Handler handler){
        this.nomeVoce = nomeVoce;
        this.handler = handler;
    }
    public String getNomeVoce(){
        return this.nomeVoce;
    }
    public Handler getHandler(){
        return this.handler;
    }
}
