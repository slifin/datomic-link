(ns history
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
  (d/q '[:find (max 5 ?tx)
         :where
         [?tx :db/txInstant]]
        db)
  (def txid 13194139533327)
  @(def db-before (d/as-of db txid))

  (d/q '[:find ?sku ?count
         :where
         [?inv :inv/sku ?sku]
         [?inv :inv/count ?count]]
       db-before))
