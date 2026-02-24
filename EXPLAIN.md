### Оптимизация получения истории документа

EXPLAIN ANALYZE
SELECT * FROM document_history 
WHERE document_id = 973;

Поиск без индекса — это full scan. Результат показывает большие затраты ресурсов и времени:
`"Seq Scan on document_history  (cost=0.00..122.12 rows=2 width=75) (actual time=0.162..0.359 rows=3 loops=1)"`

**После создания индекса:**
CREATE INDEX idx_document_history_document_id ON document_history (document_id)

Видно, что используемые ресурсы (cost) сократились, а затраченное время уменьшилось почти в 10 раз:
`"Index Scan using idx_document_history_document_id on document_history  (cost=0.28..9.21 rows=2 width=75) (actual time=0.036..0.038 rows=3 loops=1)"`

### Оптимизация пакетной выборки с сортировкой (500+ ID)

EXPLAIN ANALYZE
SELECT * FROM documents
WHERE id IN (100, 200, 300, 400, 500)
ORDER BY created_at DESC
LIMIT 10;

База находит документы по ID, но затем выполняет сортировку в оперативной памяти:
`"Sort Method: quicksort  Memory: 25kB"`

**После создания индекса:**
CREATE INDEX idx_documents_created_at ON documents (created_at DESC);

В плане запроса операция `Sort` исчезла и сменилась на:
`Index Scan using idx_documents_created_at on documents`
База данных не тратит ресурсы на сортировку, но если данных мало, планировщик может проигнорировать индекс и сделать full scan. 