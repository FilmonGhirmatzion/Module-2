package com.techelevator.auctions.controller;
import com.techelevator.auctions.DAO.AuctionDAO;
import com.techelevator.auctions.DAO.MemoryAuctionDAO;
import com.techelevator.auctions.model.Auction;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/auctions")
public class AuctionController {
    private AuctionDAO dao;
    public AuctionController() {
        this.dao = new MemoryAuctionDAO();
    }
    @RequestMapping(path= "", method = RequestMethod.GET)
    public  List<Auction> list(@RequestParam(value = "title_like", required=false,defaultValue="") String titleLike,
                               @RequestParam(value = "currentBid_lte", required=false,defaultValue="0") double current_bid
    ){
        if(!titleLike.isEmpty() && current_bid>0){
            return dao.searchByTitleAndPrice(titleLike,current_bid);
        }else if(!titleLike.isEmpty()){
            return dao.searchByTitle(titleLike);
        }else if(current_bid>0){
            return dao.searchByPrice(current_bid);
        }else{
            return dao.list();
        }
    }
    @RequestMapping(path= "{id}", method = RequestMethod.GET)
    public Auction get(@PathVariable int id) {
        return dao.get(id);
    }
    @RequestMapping(path= "" , method = RequestMethod.POST)
    public Auction create(@RequestBody Auction auction){
        return dao.create(auction);
    }
}