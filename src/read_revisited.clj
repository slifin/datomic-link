(ns read-revisited
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

;; params
(comment
  (d/q '[:find ?sku
         :in $ ?inv
         :where
         [?item :item/id ?inv]
         [?order :order/items ?item]
         [?order :order/items ?other-item]
         [?other-item :item/id ?other-inv]
         [?other-inv :inv/sku ?sku]]
       db
       [:inv/sku "SKU-25"]))


(comment
  (def rules
    '[[(ordered-together ?inv ?other-inv)
       [?item :item/id ?inv]
       [?order :order/items ?item]
       [?order :order/items ?other-item]
       [?other-item :item/id ?other-inv]]])

  (d/q '[:find ?sku
         :in $ % ?inv
         :where
         (ordered-together ?inv ?other-inv)
         [?other-inv :inv/sku ?sku]]
       db
       rules
       [:inv/sku "SKU-25"]))


(comment
  (d/q '[:find ?e ?count
         :where
         [?e :item/count ?count]
         [_ :order/items ?e]]
        db)

  (d/q '[:find ?e ?count
         :in $ ?e
         :where
         [?e :item/count ?count]]
       db
       212e5341206765719))

(comment
  (d/pull db '[*] [:inv/sku "SKU-25"]))
