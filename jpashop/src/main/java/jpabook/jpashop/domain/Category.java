package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item"
            ,joinColumns = @JoinColumn(name = "category_id")//중간 테이블의 카테고리 아이디
            , inverseJoinColumns = @JoinColumn(name = "item_id") // 이 테이블에서 아이템쪽으로 들어오는 컬럼
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // 연관관계 메소드
    public void addChildCategory(Category child){
        //child를 집어넣으면 양쪽이 다 들어가야함
        //부모도 들어가고 차일드도 들어가야함
        this.child.add(child);
        child.setParent(this);
    }


}
