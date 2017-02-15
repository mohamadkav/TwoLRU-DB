package edu.ce.sharif.twolru.db.datatypes;

import javax.persistence.*;

/**
 * Created by mohammad on 2/14/17.
 */
@Entity
@Table(name = "PAGES", indexes = {
        @Index(columnList = "LAST_HIT", name = "LAST_HIT_INDEX") })
public class Page {

    public Page(Long address) {
        setAddress(address);
    }

    public Page() {
    }

    @Id
    @Column(name = "ADDRESS", unique = true, nullable = false)
    private Long address;

    @Column(name="HITCOUNT")
    private Long hitCount;

    @Column(name="LAST_HIT",unique = true)
    private Long lastHit;

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }

    public Long getHitCount() {
        return hitCount;
    }

    public void setHitCount(Long hitCount) {
        this.hitCount = hitCount;
    }

    public Long getLastHit() {
        return lastHit;
    }

    public void setLastHit(Long lastHit) {
        this.lastHit = lastHit;
    }
}
