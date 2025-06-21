import { fetchWithAuth, logout } from './auth.js';
import { Timer } from './timer.js';

const API_BASE_URL = '/api';

const TIME_BLOCK_MAP = {
  DAY: { id: 1, type: 'DAY' },
  WEEK: { id: 2, type: 'WEEK' },
  MONTH: { id: 3, type: 'MONTH' }
};

let currentTimeType = 'DAY';
let currentTaskId = null;

// Таймер
const timer = new Timer(document.getElementById('timer-display'));
document.getElementById('start-timer').addEventListener('click', () => timer.start());
document.getElementById('pause-timer').addEventListener('click', () => timer.pause());
document.getElementById('reset-timer').addEventListener('click', () => timer.reset());

// Logout
document.getElementById('logout-btn').addEventListener('click', logout);

// Модальное окно
const modal = document.getElementById('note-modal');
const saveBtn = document.getElementById('save-note-btn');
const cancelBtn = document.getElementById('cancel-note-btn');
const titleInput = document.getElementById('note-title');
const textInput = document.getElementById('note-text');
const tagSelect = document.getElementById('note-tag');

// CRUD
async function createTask(taskData) {
  const timeBlock = TIME_BLOCK_MAP[taskData.timeBlockType];

  const response = await fetchWithAuth(`${API_BASE_URL}/tasks`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      title: taskData.title,
      description: taskData.description,
      tag: taskData.tag,
      done: false,
      timeBlockId: timeBlock.id
    })
  });
  return await response.json();
}

async function getTasks() {
  const response = await fetchWithAuth(`${API_BASE_URL}/tasks`);
  const tasks = await response.json();

  return tasks.map(task => {
    const key = Object.keys(TIME_BLOCK_MAP).find(
      k => TIME_BLOCK_MAP[k].type === task.timeBlockType
    );
    return {
      ...task,
      timeBlock: TIME_BLOCK_MAP[key]
    };
  });
}

async function updateTask(id, taskData) {
  const timeBlock = TIME_BLOCK_MAP[taskData.timeBlockType];

  const response = await fetchWithAuth(`${API_BASE_URL}/tasks/${id}`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      title: taskData.title,
      description: taskData.description,
      tag: taskData.tag,
      done: taskData.done,
      timeBlockId: timeBlock.id
    })
  });
  return await response.json();
}

async function deleteTask(id) {
  await fetchWithAuth(`${API_BASE_URL}/tasks/${id}`, { method: 'DELETE' });
}

function renderTasks(tasks) {
  document.querySelectorAll('.notes').forEach(container => {
    container.innerHTML = '';
  });

  tasks.forEach(task => {
    const timeBlockType = task.timeBlock?.type || 'DAY';
    const container = document.getElementById(`${timeBlockType.toLowerCase()}-notes`);
    if (container) {
      container.appendChild(createTaskElement(task));
    }
  });
}

function createTaskElement(task) {
  const element = document.createElement('div');
  element.className = 'note';
  element.dataset.id = task.id;
  element.dataset.tag = task.tag;
  if (task.done) element.classList.add('done');

  element.innerHTML = `
    <strong>${task.title}</strong>
    <p>${task.description}</p>
    <div class="status-toggle">
      <input type="checkbox" ${task.done ? 'checked' : ''} id="done-${task.id}" />
      <label for="done-${task.id}">Done</label>
    </div>
    <div class="note-actions">
      <button class="note-delete">×</button>
      <button class="note-edit">✏️</button>
    </div>
  `;

  // Удаление
  element.querySelector('.note-delete').addEventListener('click', async () => {
    await deleteTask(task.id);
    const tasks = await getTasks();
    renderTasks(tasks);
  });

  // Редактирование
  element.querySelector('.note-edit').addEventListener('click', () => {
    openEditModal(task);
  });

  // Переключение статуса done
  element.querySelector(`#done-${task.id}`).addEventListener('change', async e => {
    const updated = { ...task, done: e.target.checked };
    await updateTask(task.id, updated);
    const tasks = await getTasks();
    renderTasks(tasks);
  });

  return element;
}

function openAddModal(timeType) {
  currentTimeType = timeType;
  currentTaskId = null;
  titleInput.value = '';
  textInput.value = '';
  tagSelect.value = 'MAIN';
  modal.classList.add('show');
}

function openEditModal(task) {
  currentTaskId = task.id;
  currentTimeType = task.timeBlock?.type || 'DAY';
  titleInput.value = task.title;
  textInput.value = task.description;
  tagSelect.value = task.tag || 'MAIN';
  modal.classList.add('show');
}

function closeModal() {
  modal.classList.remove('show');
}

async function saveNote() {
  const title = titleInput.value.trim();
  const description = textInput.value.trim();
  const tag = tagSelect.value;

  if (!title || !description) {
    alert('Please fill all fields');
    return;
  }

  try {
    const taskData = {
      title,
      description,
      tag,
      timeBlockType: currentTimeType,
      done: false
    };

    if (currentTaskId) {
      const oldTask = await fetchWithAuth(`${API_BASE_URL}/tasks/${currentTaskId}`);
      const existing = await oldTask.json();
      taskData.done = existing.done;
      await updateTask(currentTaskId, taskData);
    } else {
      await createTask(taskData);
    }

    closeModal();
    const tasks = await getTasks();
    renderTasks(tasks);
  } catch (error) {
    console.error('Error saving note:', error);
    alert('Failed to save note: ' + error.message);
  }
}

// Инициализация

document.addEventListener('DOMContentLoaded', async () => {
  if (!localStorage.getItem('jwt')) {
    window.location.href = '/login.html';
    return;
  }

  document.querySelectorAll('.add-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const timeType = btn.closest('.tab').dataset.section;
      openAddModal(timeType);
    });
  });

  saveBtn.addEventListener('click', saveNote);
  cancelBtn.addEventListener('click', closeModal);

  try {
    const tasks = await getTasks();
    renderTasks(tasks);
  } catch (error) {
    console.error('Failed to load tasks:', error);
  }
});
