package com.MaxEradus.rikken.services;

import com.MaxEradus.rikken.Repos.TakkieRepository;
import com.MaxEradus.rikken.model.Ronde;
import com.MaxEradus.rikken.model.Takkie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/takkie")
public class TakkieController {

    @Autowired
    public final TakkieRepository takkieRepository;
    public final PlayerController playerController;
    public final RondeController rondeController;

    public TakkieController(TakkieRepository takkieRepository, PlayerController playerController, RondeController rondeController) {
        this.takkieRepository = takkieRepository;
        this.playerController = playerController;
        this.rondeController = rondeController;
    }

    public Takkie createTakkieWhole(Ronde ronde1, Ronde ronde2, Ronde ronde3, Ronde ronde4) {
        Takkie takkie = new Takkie(ronde1, ronde2, ronde3, ronde4);
        return takkieRepository.save(takkie);
    }

    public Takkie createTakkieEmpty() {
        Takkie takkie = new Takkie();
        return takkieRepository.save(takkie);
    }

    public Takkie getTakkieById(Long id) {
        return takkieRepository.findById(id).orElse(null);
    }

    public void deleteTakkieById(Long id) {
        takkieRepository.deleteById(id);
    }

}
