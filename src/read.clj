(ns read
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
  (def db (d/db conn))
  (def query (d/pull db [{:inv/color [:db/ident]}
                         {:inv/size [:db/ident]}
                         {:inv/type [:db/ident]}]
                        [:inv/sku "green"])))





