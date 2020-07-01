### Pick event type specification

|  Field      | Type    | Description                                                                                        |
|:-----------:|:-------:|:--------------------------------------------------------------------------------------------------:|
| `id`        | String  | A unique identifier                                                                                |
| `timestamp` | String  | The time at which the event was emitted; formatted as an [ISO 8601][iso-8601] UTC date-time string |
| `picker`    | Object  | Picker object, see below                                                                           |
| `article`   | Object  | Article object, see below                                                                          |
| `quantity`  | Integer | The number of articles picked                                                                      |

### Picker type specification

| Field           | Type   | Description                                                                                                |
|:---------------:|:------:|:----------------------------------------------------------------------------------------------------------:|
| `id`            | String | A unique identifier                                                                                        |
| `name`          | String | The person's name                                                                                          |
| `active_since`  | String | The time the picker clocked in to start working; formatted as an [ISO 8601][iso-8601] UTC date-time string |

### Article type specification

| Field              | Type   | Description                     |
|:------------------:|:------:|:-------------------------------:|
| `id`               | String | A unique identifier             |
| `name`             | String | An English, human-readable name |
| `temperature_zone` | String | Either `ambient` or `chilled`   |

### Example input event JSON representation

The following JSON object is an example of the kind of event that may be
received from the `InputStream`. Note how it matches the specification above.
There is one difference: you may assume that the events read from the
`InputStream` comprise a single line. (I.e., they are not formatted.)

```json
{
  "timestamp": "2018-12-20T11:50:48Z",
  "id": "2344",
  "picker": {
    "id": "14",
    "name": "Joris",
    "active_since": "2018-12-20T08:20:15Z"
  },
  "article": {
    "id": "13473",
    "name": "ACME Bananas",
    "temperature_zone": "ambient"
  },
  "quantity": 2
}
```

## Task ##

* A processor must write the result of the aforementioned filter, group and
  sort operations to the provided `OutputStream` according the following JSON
  format:
  ```json
  [
    {
      "picker_name": "Joris",
      "active_since": "2018-09-20T08:20:00Z",
      "picks": [
        {
          "article_name": "ACME BANANAS",
          "timestamp": "2018-12-20T11:50:48Z"
        },
        ... more picks here ...
      ]
    },
    ... more pickers here ...
  ]
  ```

For a complete example of expected input and corresponding output, compare the
contents of the following two provided files:
- [`happy-path-input.json-stream`](./src/test/resources/tech/picnic/assignment/impl/happy-path-input.json-stream)
- [`happy-path-output.json`](./src/test/resources/tech/picnic/assignment/impl/happy-path-output.json)

