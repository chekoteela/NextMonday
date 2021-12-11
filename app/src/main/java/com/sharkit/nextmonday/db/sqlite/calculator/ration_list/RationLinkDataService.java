package com.sharkit.nextmonday.db.sqlite.calculator.ration_list;

import android.content.Context;

import com.sharkit.nextmonday.db.sqlite.calculator.product.ProductPFCDataService;
import com.sharkit.nextmonday.entity.calculator.LinkFoodDTO;
import com.sharkit.nextmonday.entity.calculator.PFC;
import com.sharkit.nextmonday.entity.calculator.PortionDTO;

import java.util.ArrayList;

public class RationLinkDataService {
    private final Context context;
    private final RationLinkData rationLinkData;

    public RationLinkDataService(Context context) {
        rationLinkData = new RationLinkData(context);
        rationLinkData.onCreate(rationLinkData.getReadableDatabase());
        this.context = context;
    }

    public void create(LinkFoodDTO linkFoodDTO){
        rationLinkData.create(linkFoodDTO);
    }

    public ArrayList<LinkFoodDTO> findAllLinkByMealAndDate(String meal, String date) {
      return rationLinkData.findByMealAndDate(meal, date);
    }

    public ArrayList<LinkFoodDTO> findAllLinkByDate(String date) {
      return rationLinkData.findAllByDate(date);
    }

    public ArrayList<PFC> findLAllSumByDate(String date) {
        ProductPFCDataService service = new ProductPFCDataService(context);
        ArrayList<PFC> pfc = new ArrayList<>();
        for (int i = 0; i < findAllLinkByDate(date).size(); i++) {
           pfc.add(service.findByID(findAllLinkByDate(date).get(i).getLink()).transform(new PFC()));
        }
        return pfc;
    }
    public ArrayList<PortionDTO> findGeneralAndFoodPortion(String date){
        ProductPFCDataService service = new ProductPFCDataService(context);
        ArrayList<PortionDTO> portionDTOS = new ArrayList<>();
        for (int i = 0; i < findAllLinkByDate(date).size(); i++) {
            PortionDTO portionDTO = new PortionDTO();
            portionDTO.setGeneralPortion(service.findByID(findAllLinkByDate(date).get(i).getLink()).getPortion());
            portionDTO.setFoodPortion(findAllLinkByDate(date).get(i).getPortion());
            portionDTOS.add(portionDTO);
        }
        return portionDTOS;
    }
}
