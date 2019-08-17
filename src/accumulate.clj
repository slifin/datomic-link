(ns accumulate
  (:require [datomic.client.api :as d]))

(comment
  (def cfg {
            :server-type :ion
            :region "eu-west-1"
            :system "personal-datomic"
            :endpoint "http://entry.personal-datomic.eu-west-1.datomic.net:8182"
            :proxy-port 8182})
  (def client (d/client cfg))
  (def conn (d/connect client {:db-name "tutorial"}))
  (def db (d/db conn)))

(comment
  (def order-schema
    [{:db/ident :order/items
      :db/valueType :db.type/ref
      :db/cardinality :db.cardinality/many
      :db/isComponent true}
     {:db/ident :item/id
      :db/valueType :db.type/ref
      :db/cardinality :db.cardinality/one}
     {:db/ident :item/count
      :db/valueType :db.type/long
      :db/cardinality :db.cardinality/one}])
  (d/transact conn {:tx-data order-schema}))

(comment
  (def add-order
    {:order/items [{:item/id [:inv/sku "SKU-25"]
                    :item/count 10}
                   {:item/id [:inv/sku "SKU-26"]
                    :item/count 20}]})
  (d/transact conn {:tx-data [add-order]}))







