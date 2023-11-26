export const formatTimToInterface = (time) => {
  let arrayDateTime = time.split("T");
  let time_ = arrayDateTime[1].split(".")[0];
  return arrayDateTime[0] + " " + time_;
};

export const formatInterfaceToTime = (time) => {
  let arrayDateTime = time.split(" ");
  return arrayDateTime[0] + "T" + arrayDateTime[1];
};
