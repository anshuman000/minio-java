/*
 * Minio Java Library for Amazon S3 Compatible Cloud Storage, (C) 2015 Minio, Inc.
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

package io.minio.messages;

import com.google.api.client.util.Key;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import io.minio.errors.InvalidBucketNameException;

@SuppressWarnings("SameParameterValue")
public class Bucket extends XmlEntity {
  @Key("Name")
  private String name;
  @Key("CreationDate")
  private String creationDate;
  private final SimpleDateFormat dateFormat;


  /**
   * constructor.
   */
  public Bucket() {
    super();
    super.name = "Bucket";
    dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }


  /**
   * constructor allow bucket name as argument.
   */
  public Bucket(String name) throws InvalidBucketNameException {
    this();

    if (name == null) {
      throw new InvalidBucketNameException("(null)", "null bucket name");
    }

    if (name.length() < 3 || name.length() > 63) {
      String msg = "bucket name must be at least 3 and no more than 63 characters long";
      throw new InvalidBucketNameException(name, msg);
    }

    if (name.indexOf(".") != -1) {
      String msg = "bucket name with '.' is not allowed due to SSL cerificate verification error.  "
          + "For more information refer http://docs.aws.amazon.com/AmazonS3/latest/dev/BucketRestrictions.html";
      throw new InvalidBucketNameException(name, msg);
    }

    if (!name.matches("^[a-z0-9][a-z0-9\\-]+[a-z0-9]$")) {
      String msg = "bucket name does not follow Amazon S3 standards.  For more information refer "
          + "http://docs.aws.amazon.com/AmazonS3/latest/dev/BucketRestrictions.html";
      throw new InvalidBucketNameException(name, msg);
    }

    this.name = name;
  }


  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public Date getCreationDate() throws ParseException {
    return dateFormat.parse(creationDate);
  }


  public void setCreationDate(Date creationDate) {
    this.creationDate = dateFormat.format(creationDate);
  }


  /**
   * setter for creationDate.
   */
  public void setCreationDate(String creationDate) throws ParseException {
    // make sure creationDate is formatted correctly
    dateFormat.parse(creationDate);
    this.creationDate = creationDate;
  }
}
