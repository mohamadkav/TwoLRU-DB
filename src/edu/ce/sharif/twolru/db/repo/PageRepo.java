package edu.ce.sharif.twolru.db.repo;

import edu.ce.sharif.twolru.db.RootMgr;
import edu.ce.sharif.twolru.db.datatypes.Page;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Created by mohammad on 2/14/17.
 */
public class PageRepo extends RootMgr{
    public static Page getByAddress(Long address){
        return getInstance().get(Page.class,address);
    }
    public static Page getNewestPage(){
        Criteria criteria=getInstance().createCriteria(Page.class);
        criteria.addOrder(Order.desc("lastHit"));
        criteria.setMaxResults(1);
        return (Page) criteria.uniqueResult();
    }
    public static Long getCount(){
        Criteria criteria=getInstance().createCriteria(Page.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
    public static Long getOldestPageHit(){
        Criteria criteria=getInstance().createCriteria(Page.class);
        criteria.addOrder(Order.asc("lastHit"));
        criteria.setMaxResults(1);
        Page page=(Page) criteria.uniqueResult();
        if(page==null)
            return 0L;
        return page.getLastHit();
    }
    public static Long getNewestPageHit(){
        Criteria criteria=getInstance().createCriteria(Page.class);
        criteria.addOrder(Order.desc("lastHit"));
        criteria.setMaxResults(1);
        Page page=(Page) criteria.uniqueResult();
        if(page==null)
            return 1L;
        return page.getLastHit();
    }
}
