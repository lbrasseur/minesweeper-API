import { ResultDto } from '../service/dto/result-dto';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export function extractPayload<T>(operation: Observable<ResultDto<T>>): Observable<T> {
  return operation.pipe(map(result => {
    if (result.error != null) {
      alert(result.error); // TODO: implement nicer error reporting
      throw result.error;
    } else {
      return result.payload;
    }
  }));
}
