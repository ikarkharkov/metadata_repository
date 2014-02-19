package com.chystopo.metarepository;

import com.chystopo.metarepository.bean.Item;

import java.io.InputStream;
import java.util.Collection;

public interface IRepository {

    /**
     * Load XML given context.
     *
     * @param context identifier of model/connections
     * @param is      XML input
     */
    void load(String context, InputStream is);

    /**
     * Direct children of item. Children of elements are not initialized.
     *
     * @param parent
     * @return children on first line or empty collection if no children
     */
    Collection<? extends Item> findChildren(Item parent);


    /**
     * Search children recursively. Children of elements are initialized recursively.
     *
     * @param parent
     * @param recursively
     * @return
     */
    Collection<? extends Item> findChildren(Item parent, boolean recursively);

    /**
     * Find parents recursively, so each parent item will have initialized parent if it exists
     *
     * @param item
     * @return
     */
    Collection<? extends Item> findParents(Item item);

    /**
     * Find impacted items.
     *
     * @param source
     * @return collection of items with same type as source. So return Columns if source is Column
     * and Tables if source is Table
     */
    Collection<? extends Item> findTargets(Item source);

    /**
     * Find linage
     *
     * @param target
     * @return collection of items with same type as source. So return Columns if target is Column
     * and Tables is source is Table.
     */
    Collection<? extends Item> findSources(Item target);
}
