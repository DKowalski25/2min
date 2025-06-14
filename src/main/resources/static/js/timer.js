export class Timer {
  constructor(displayElement) {
    this.display = displayElement;
    this.startTime = 0;
    this.elapsedTime = 0;
    this.timerInterval = null;
  }

  start() {
    if (this.timerInterval) return;

    this.startTime = Date.now() - this.elapsedTime;
    this.timerInterval = setInterval(() => {
      this.elapsedTime = Date.now() - this.startTime;
      this.updateDisplay();
    }, 1000);
  }

  pause() {
    clearInterval(this.timerInterval);
    this.timerInterval = null;
  }

  reset() {
    clearInterval(this.timerInterval);
    this.timerInterval = null;
    this.elapsedTime = 0;
    this.updateDisplay();
  }

  updateDisplay() {
    const totalSeconds = Math.floor(this.elapsedTime / 1000);
    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    const seconds = totalSeconds % 60;

    this.display.textContent =
      `${hours.toString().padStart(2, '0')}:` +
      `${minutes.toString().padStart(2, '0')}:` +
      `${seconds.toString().padStart(2, '0')}`;
  }
}