(ns retract
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
  (def inventory-counts
    [
     {:db/ident :inv/count
      :db/valueType :db.type/long
      :db/cardinality :db.cardinality/one}])
  (d/transact conn {:tx-data inventory-counts}))

(comment
  (def inventory-update
    [
     [:db/add [:inv/sku "SKU-21"] :inv/count 7]
     [:db/add [:inv/sku "SKU-22"] :inv/count 7]
     [:db/add [:inv/sku "SKU-42"] :inv/count 100]])
  (d/transact conn {:tx-data inventory-update}))

(comment
  (d/transact
    conn
    {:tx-data [[:db/retract [:inv/sku "SKU-22"] :inv/count 7]
               [:db/add "datomic.tx" :db/doc "remove incorrect assertion"]]}))


(comment
  (d/pull db '[*] [:inv/sku "SKU-42"]))

(comment
  (d/transact
    conn
    {:tx-data
     [[:db/add [:inv/sku "SKU-42"] :inv/count 1000]
      [:db/add "datomic.tx" :db/doc "correct data entry error"]]}))

(comment
  (d/q '[:find ?sku ?count
         :where
         [?inv :inv/sku ?sku]
         [?inv :inv/count ?count]]
       db))
