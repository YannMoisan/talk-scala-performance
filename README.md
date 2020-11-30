## HashCodeBench

|             |          | time (ns/op) | alloc rate norm |
|-------------|----------|--------------|-----------------|
| int         | hashcode | 3,876        | 16              |
| int         | ##       | 1,982        | 0               |
| long        | hashcode | 3,952        | 24              |
| long        | ##       | 2,29         | 0               |
| wrapped     | hashcode | 4,843        | 0               |
| wrapped     | ##       | 4,86         | 0               |
| value class | hashcode | 3,317        | 16              |
| value class | ##       | 3,37         | 16              |