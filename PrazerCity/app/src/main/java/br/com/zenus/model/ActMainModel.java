package br.com.zenus.model;

import br.com.zenus.main.MVP;

/**
 * Created by Tuca on 15/07/2017.
 */

public class ActMainModel implements MVP.ModelImpl {

    private MVP.PresenterImpl presenter;


    public ActMainModel(MVP.PresenterImpl presenter){
        this.presenter = presenter;
    }
}
