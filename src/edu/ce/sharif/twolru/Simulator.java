package edu.ce.sharif.twolru;

import edu.ce.sharif.twolru.db.datatypes.Page;
import edu.ce.sharif.twolru.db.repo.PageRepo;

import java.util.*;

/**
 * Created by mohammad on 1/30/17.
 */
public class Simulator {


    private Long migToDram = 0L;
    private Long dramRead;
    private Long dramWrite;
    private Long pcmRead;
    private Long pcmWrite;
    private Long migToPcm = 0L;

    private final Long DRAM_SIZE;
    private final Long PCM_SIZE;
    private final Long PCM_THRESHOLD;



    public Simulator(Long dramSize, Long pcmSize, Long pcmThreshold)
    {
        this.DRAM_SIZE = dramSize;
        this.PCM_SIZE = pcmSize;
        this.PCM_THRESHOLD = pcmThreshold;
    }

    private List<Page> dram = new ArrayList<Page>(){
        @Override
        public boolean add(Page page) {
            if(this.size()==DRAM_SIZE) {
                migToPcm++;
                addToPcm(dram.get(0));
                dram.remove(dram.size()-1);
                page.setHitCount(0L);
                dram.add(0,page);
                return true;
            }
            else
                add(0,page);
            return true;
        }
    };

    private void addToPcm(Page page){
        if(PageRepo.getCount().equals(PCM_SIZE)) {
            throw new RuntimeException("UNEXPECTED :))))");
        }
        else {
            page.setLastHit(PageRepo.getNewestPageHit()+1);
            page.setHitCount(0L);
            PageRepo.add(page);
        }
    }


    public void printStatistics(){
        System.out.println("migration to dram: "+migToDram);
        System.out.println("migration to pcm: "+ migToPcm);
    }

    public int add(Page newPage)
    {
        int dramIndex=dram.indexOf(newPage);
        if(dramIndex!=-1) {
            Page p=new Page( dram.get(dramIndex).getAddress());
            p.setHitCount(dram.get(dramIndex).getHitCount()+1);

            dram.remove(dramIndex);
            dram.add(p);
            //dram.get(dramIndex).setAccessNum(dram.get(dramIndex).getAccessNum()+1);
        }
        //else
        //    dram.add(newPage);
        else {
            Page page=PageRepo.getByAddress(newPage.getAddress());
            if(page!=null)
            {
                page.setHitCount(page.getHitCount()+1);
                page.setLastHit(PageRepo.getNewestPageHit()+1);
                PageRepo.update(page);
                if(PageRepo.getNewestPage().getHitCount() >= PCM_THRESHOLD) {
                    migToDram++;
                    Page p=new Page(PageRepo.getNewestPage().getAddress());
                    PageRepo.delete(p);
                    p.setHitCount(0L);
                    dram.add(p);
                }

            }
            else{
                if(dram.size()==DRAM_SIZE) {
                    if (PageRepo.getCount().equals(PCM_SIZE)) {
                        throw new RuntimeException("UNEXPECTED");
              //          pcm.remove(pcm.size()-1);
                    }
                    newPage.setLastHit(PageRepo.getOldestPageHit()-1);
                    newPage.setHitCount(0L);
                    PageRepo.add(newPage);
                }
                else
                    dram.add(newPage);
            }
        }
        return 0;
    }

}
