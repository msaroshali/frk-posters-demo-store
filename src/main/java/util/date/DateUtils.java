/*
 * Copyright (c) 2013-2023 Xceptance Software Technologies GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date utility class.
 * 
 * @author sebastianloob
 */
public class DateUtils
{

    /**
     * Returns the current date.
     * 
     * @return
     */
    public static String getCurrentDate()
    {
        final Date dt = new Date();
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dt);
    }
}
